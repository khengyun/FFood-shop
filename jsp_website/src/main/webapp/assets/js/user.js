$(document).ready(function () {
  // Creates a dictionary of tabIDs and their corresponding tab links
  const tabLinksDict = {
    null: "info",
    "0": "info",
    "1": "account",
    "2": "order"
  };
  let tabID = document.getElementsByClassName("tab-content").item(0).getAttribute("data-initial-tab");
  
  if (tabID === null || tabID === undefined || tabID === "") {
    tabID = "0";
  }
  
  // Get the initial tabID ("home", "foods", etc. from the tabID index)
  const initialTabID = tabLinksDict[tabID];
  
  const tabLink = document.querySelector("ul [data-bs-target='#" + initialTabID + "']");
  
  bootstrap.Tab.getOrCreateInstance(tabLink).show();
});