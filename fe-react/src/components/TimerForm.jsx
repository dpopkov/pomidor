import { useState } from 'react';
import ApiClient from '../ApiClient';

export default function TimerForm() {
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
      <TestCreate />
      <TestGetAll />
    </form>
  );
}

function TestCreate() {
  const [count, setCount] = useState(1);

  const postPomidor = async (e) => {
    e.preventDefault();

    const start = 3000 + count;
    const end = 3100 + count;
    const resp = await ApiClient.sendPomidor(`test-fe-${count}`, start, end);
    if (resp.ok) {
      const result = await resp.json();
      console.log('Result:', result);
      setCount((c) => c + 1);
    } else {
      console.log('Error creating pomidor');
    }
  };
  return <button onClick={postPomidor}>Test Create</button>;
}

function TestGetAll() {
  const getAll = async (e) => {
    e.preventDefault();

    const resp = await ApiClient.getAllPomidors();
    if (resp.ok) {
      const all = await resp.json();
      console.log('All: ', all);
    } else {
      console.log('Error getting all pomidors');
    }
  };
  return <button onClick={getAll}>Test Get All</button>;
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
