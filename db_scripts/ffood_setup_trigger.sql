--Use ffood database
USE ffood
GO

-- Remove link food to other database after delete food
CREATE TRIGGER tr_delete_admin_food_links
ON Food
FOR DELETE
AS
BEGIN
    DELETE FROM AdminFood WHERE food_id IN (SELECT deleted.food_id FROM deleted);
END

go

-- Check price and discount percent before add to Food table
CREATE TRIGGER tr_check_food_price
ON Food
AFTER DELETE
AS
BEGIN
    IF (SELECT COUNT(*) FROM inserted WHERE food_price <= 0 OR discount_percent < 0 OR discount_percent > 100) > 0
    BEGIN
        RAISERROR('Invalid food price or discount percent.', 16, 1)
        ROLLBACK
    END
END

GO

-- Remove cart after customer deleted

CREATE TRIGGER tr_delete_cart_links
ON Account
AFTER DELETE
AS
BEGIN
    DELETE FROM Cart WHERE customer_id IN (SELECT deleted.customer_id FROM deleted);
END

go

-- Update order status

CREATE TRIGGER tr_update_order_status_cancel
ON [Order]
AFTER UPDATE
AS
BEGIN
    IF UPDATE(order_cancel_time)
    BEGIN
        UPDATE [Order]
        SET order_status_id = (SELECT order_status_id FROM OrderStatus WHERE order_status = 'Đã hủy')
        WHERE order_id = (SELECT order_id FROM inserted);
    END
END

go

-- Don't delete when still have order
CREATE TRIGGER tr_prevent_delete_customer
ON Customer
INSTEAD OF DELETE
AS
BEGIN
    IF (EXISTS (SELECT 1 FROM Cart WHERE customer_id = (SELECT customer_id FROM deleted)) OR
        EXISTS (SELECT 1 FROM [Order] WHERE customer_id = (SELECT customer_id FROM deleted)))
    BEGIN
        RAISERROR('Cannot delete customer with active cart or orders.', 16, 1)
    END
    ELSE
    BEGIN
        DELETE FROM Customer WHERE customer_id = (SELECT customer_id FROM deleted)
    END
END

go

-- remove food from cart when a food was removed 

CREATE TRIGGER tr_remove_food_from_carts
ON Food
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    CREATE TABLE #CartsToRemove (cart_id INT);

    INSERT INTO #CartsToRemove (cart_id)
    SELECT DISTINCT ci.cart_id
    FROM CartItem ci
    INNER JOIN deleted d ON ci.food_id = d.food_id;

    DELETE FROM CartItem WHERE food_id IN (SELECT food_id FROM deleted);
    
    DELETE FROM Food WHERE food_id IN (SELECT food_id FROM deleted);
    
    DELETE FROM Cart WHERE cart_id IN (SELECT cart_id FROM #CartsToRemove);
    
    DROP TABLE #CartsToRemove;
END

