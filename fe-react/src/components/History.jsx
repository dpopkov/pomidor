const historyData = [
  { start: '1230', description: 'read', end: '1330' },
  { start: '1330', description: 'write', end: '1430' },
];

export default function History() {
  return (
    <div className="history">
      <h3>History</h3>
      <HistoryFilter />
      <HistoryItems data={historyData} />
    </div>
  );
}

function HistoryFilter() {
  return (
    <div className="filter">
      <input type="text" className="input-filter" placeholder="Filter items" />
    </div>
  );
}

function HistoryItems({ data }) {
  return (
    <ul className="item-list">
      {data.map((el, index) => (
        <HistoryItem item={el} key={index} />
      ))}
    </ul>
  );
}

function HistoryItem({ item }) {
  function calcDuration(start, end) {
    const startMin = parseInt(start.slice(0, 2));
    const startSec = parseInt(start.slice(2, 4));
    const endMin = parseInt(end.slice(0, 2));
    const endSec = parseInt(end.slice(2, 4));
    const min = endMin - startMin;
    const sec = endSec - startSec;
    return `[${min}:${sec < 10 ? '0' : ''}${sec}]`;
  }

  return (
    <li>
      {item.start}&nbsp;
      {item.description}&nbsp;
      {item.end}&nbsp;
      {calcDuration(item.start, item.end)}
    </li>
  );
}
