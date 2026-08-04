import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import { Activity, ShieldAlert, Thermometer, Wind } from 'lucide-react';

export default function App() {
  const [telemetry, setTelemetry] = useState({
    oxigeno: 0,
    presion: 0,
    temperatura: 0,
    radiacion: 0,
    timestamp: null
  });
  const [connected, setConnected] = useState(false);

  useEffect(() => {
    // Usamos WebSocket nativo del navegador directo a Spring Boot (sin SockJS)
    const client = new Client({
      brokerURL: 'ws://localhost:8080/ws-ares/websocket',
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        setConnected(true);
        client.subscribe('/topic/telemetry', (message) => {
          if (message.body) {
            const data = JSON.parse(message.body);
            setTelemetry(data);
          }
        });
      },
      onDisconnect: () => {
        setConnected(false);
      },
      onStompError: (frame) => {
        console.error('Broker STOMP error: ' + frame.headers['message']);
        setConnected(false);
      }
    });

    client.activate();

    return () => {
      client.deactivate();
    };
  }, []);

  return (
    <div className="min-h-screen bg-slate-950 p-6 text-slate-100 font-sans">
      {/* Header Panel */}
      <header className="flex justify-between items-center mb-8 pb-4 border-b border-slate-800">
        <div>
          <h1 className="text-3xl font-bold tracking-wider text-cyan-400 uppercase">
            Ares Mission Control
          </h1>
          <p className="text-slate-400 text-sm">Monitoreo Ambiental Marte - Base Alfa</p>
        </div>
        <div className="flex items-center gap-3">
          <span className="text-xs uppercase tracking-widest text-slate-400">Estado Conexión:</span>
          <span className={`inline-block w-3 h-3 rounded-full ${connected ? 'bg-emerald-500 animate-pulse' : 'bg-red-500'}`}></span>
          <span className="text-sm font-semibold">{connected ? 'ONLINE (WS)' : 'OFFLINE'}</span>
        </div>
      </header>

      {/* Grid de Métricas */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        
        {/* Nivel de Oxígeno */}
        <div className="bg-slate-900 border border-slate-800 p-5 rounded-xl">
          <div className="flex justify-between items-center mb-2">
            <span className="text-slate-400 font-medium text-sm">Oxígeno</span>
            <Activity className="text-cyan-400" size={20} />
          </div>
          <div className="text-3xl font-extrabold text-cyan-400">
            {telemetry.oxigeno ? telemetry.oxigeno.toFixed(1) : '--'} %
          </div>
          <p className="text-xs text-slate-500 mt-2">Nivel óptimo para módulo habitacional</p>
        </div>

        {/* Presión */}
        <div className="bg-slate-900 border border-slate-800 p-5 rounded-xl">
          <div className="flex justify-between items-center mb-2">
            <span className="text-slate-400 font-medium text-sm">Presión Barométrica</span>
            <Wind className="text-emerald-400" size={20} />
          </div>
          <div className="text-3xl font-extrabold text-emerald-400">
            {telemetry.presion ? telemetry.presion.toFixed(2) : '--'} atm
          </div>
          <p className="text-xs text-slate-500 mt-2">Presión interna estabilizada</p>
        </div>

        {/* Temperatura Exterior */}
        <div className="bg-slate-900 border border-slate-800 p-5 rounded-xl">
          <div className="flex justify-between items-center mb-2">
            <span className="text-slate-400 font-medium text-sm">Temp. Exterior</span>
            <Thermometer className="text-amber-400" size={20} />
          </div>
          <div className="text-3xl font-extrabold text-amber-400">
            {telemetry.temperatura ? telemetry.temperatura.toFixed(1) : '--'} °C
          </div>
          <p className="text-xs text-slate-500 mt-2">Superficie marciana exterior</p>
        </div>

        {/* Radiación */}
        <div className="bg-slate-900 border border-slate-800 p-5 rounded-xl">
          <div className="flex justify-between items-center mb-2">
            <span className="text-slate-400 font-medium text-sm">Radiación Solar</span>
            <ShieldAlert className="text-rose-400" size={20} />
          </div>
          <div className="text-3xl font-extrabold text-rose-400">
            {telemetry.radiacion ? telemetry.radiacion : '--'} mSv
          </div>
          <p className="text-xs text-slate-500 mt-2">Riesgo radiológico actual</p>
        </div>

      </div>

      {/* Footer */}
      <footer className="mt-12 text-center text-xs text-slate-600">
        Ares Space Program • Telemetría push en directo vía WebSockets STOMP • Java Spring Boot & React
      </footer>
    </div>
  );
}