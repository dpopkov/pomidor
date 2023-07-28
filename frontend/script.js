const inputFilter = document.getElementById('input-filter');
const historyList = document.getElementById('item-list');

function checkInputUi() {
  // check if the timer is working
}

function checkHistoryUi() {
  const historyIsEmpty = historyList.querySelectorAll('li').length === 0;
  inputFilter.style.display = historyIsEmpty ? 'none' : 'block';
}

checkInputUi();
checkHistoryUi();
