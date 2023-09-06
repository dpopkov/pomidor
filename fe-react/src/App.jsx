import './App.css';
import TimerForm from './components/TimerForm';
import History from './components/History';

export default function App() {
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
