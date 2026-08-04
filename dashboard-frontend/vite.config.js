import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  define: {
    // Esto resuelve el error "global is not defined" de SockJS en el navegador
    global: 'window',
  },
  server: {
    port: 3000,
    host: '0.0.0.0'
  }
})