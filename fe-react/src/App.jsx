import { useState } from 'react';
import './App.css';

export default function App() {
  const [count, setCount] = useState(0);

  return (
    <div className="container">
      <header>
        <h1 className="app-title">Pomidor</h1>
      </header>
      <main>
        <TimerForm />
        <History />
      </main>
    </div>
  );
}

function TimerForm() {
  return (
    <form className="timer-form">
      <TimeoutInput />
      <TimeoutOptions />
      <Buttons>
        <Button>Start</Button>
        <Button>Pause</Button>
        <Button>Stop</Button>
      </Buttons>
      <Output elapsedTime="2:30" />
    </form>
  );
}

function FormControl({ children }) {
  return <div className="form-control">{children}</div>;
}

function TimeoutInput() {
  return (
    <FormControl>
      <input
        type="text"
        className="form-input"
        placeholder="Enter timeout (2m30s)"
      />
    </FormControl>
  );
}

function TimeoutOptions() {
  return (
    <FormControl>
      <Radio>45m</Radio>
      <Radio isMain="true" checked="true">
        25m
      </Radio>
      <Radio>15m</Radio>
      <Radio isMain="true">5m</Radio>
      <Radio>2m</Radio>
      <Radio>1m</Radio>
    </FormControl>
  );
}

function Radio({ children, isMain, checked }) {
  const [isChecked, setIsChecked] = useState(Boolean(checked));

  function handleToggle() {
    console.log('clicked radio');
  }

  return (
    <span className={isMain ? 'main-timeout' : ''}>
      <label>
        <input type="radio" checked={isChecked} onChange={handleToggle} />
        &nbsp;{children}
      </label>
    </span>
  );
}

function Buttons({ children }) {
  return (
    <FormControl>
      <div className="buttons">{children}</div>
    </FormControl>
  );
}

function Button({ children }) {
  return <button className="btn">{children}</button>;
}

function Output({ elapsedTime }) {
  return (
    <div className="current-elapsed-time">
      <output>Elapsed time: {elapsedTime}</output>
    </div>
  );
}

const historyData = [
  { start: '1230', description: 'read', end: '1330' },
  { start: '1330', description: 'write', end: '1430' },
];

function History() {
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
