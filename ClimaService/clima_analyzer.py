from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

# Definimos el molde de los datos climáticos que nos enviará Java
class DatosMision(BaseModel):
    horas_duracion: int
    id_colono: int

print("📡 [CEREBRO PYTHON]: Sistemas analíticos meteorológicos online.")

@app.post("/analizar-riesgo")
def analizar_riesgo(mision: DatosMision):
    # Simulamos una lógica predictiva: a más horas de misión, más riesgo de tormenta de arena
    riesgo_porcentaje = mision.horas_duracion * 12
    
    # Capamos el riesgo máximo al 100%
    if riesgo_porcentaje > 100:
        riesgo_porcentaje = 100
        
    estado_mision = "SEGURO" if riesgo_porcentaje < 50 else "PELIGRO_TORMENTA"
    
    print(f"🔬 [ANALIZANDO]: Evaluando misión de {mision.horas_duracion}h para Colono #{mision.id_colono}...")
    print(f"📊 [RESULTADO]: Riesgo calculado: {riesgo_porcentaje}% -> Estado: {estado_mision}")
    
    return {
        "riesgo_porcentaje": riesgo_porcentaje,
        "estado_mision": estado_mision
    }