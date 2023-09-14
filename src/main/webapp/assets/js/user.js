$(document).ready(clickTab);

function clickTab() {
  let tabName = window.location.hash;
  let selectedTab = document.querySelector(tabName + '-tab');
  let firstTab = new bootstrap.Tab(selectedTab);
  firstTab.show();
}

