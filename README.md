# Acierta Pro — Práctica Saber Pro (Ingeniería de Sistemas)

Aplicación Android instalable desarrollada para la preparación del examen de Estado Saber Pro para Ingeniería de Sistemas en Colombia. Diseñada con un sistema Neo-Brutalista inspirado en la iconografía geométrica Wayuu y optimizada para funcionamiento 100% offline en dispositivos móviles.

---

## 1. Arquitectura y Estructura del Proyecto

```
/
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml (Orientación portrait, permiso vibración háptica, icon adaptive)
│   │   ├── java/com/example/
│   │   │   ├── MainActivity.kt (Enrutamiento central, BackHandler con doble toque en Inicio y pausa en examen)
│   │   │   ├── domain/model/ExamModels.kt (Modelos de datos: Question, ExamAttempt, EssayRubric, ModuleCode, Settings)
│   │   │   ├── data/repository/ExamRepository.kt (ExamRepository, AttemptRepository, SettingsRepository con MockStore)
│   │   │   ├── ui/
│   │   │   │   ├── theme/ (Color.kt, Theme.kt, Type.kt con tokens Neo-Brutalistas)
│   │   │   │   ├── components/ (BrutalistCard, BrutalistButton, NeoBadge, NeoSwitch, ExamTimerBadge, WayuuPatternBanner, AciertaBottomNav)
│   │   │   │   ├── screens/
│   │   │   │   │   ├── HomeScreen.kt (Splash y Pantalla de Inicio con reanudación y tarjetas directas)
│   │   │   │   │   ├── ConfigureScreen.kt (Configuración de simulacro y Resumen previo)
│   │   │   │   │   ├── ExamScreens.kt (Marco del examen, Selección múltiple, Comunicación Escrita, Inglés, Revisión previa a entrega)
│   │   │   │   │   ├── ResultsScreens.kt (Resultados globales, Revisión de preguntas, Rúbrica de ensayo, Desglose por módulo, Vista previa de informe y Exportar PDF)
│   │   │   │   │   └── UtilityScreens.kt (Historial, Estadísticas de ritmo, Respaldos JSON locales, Ajustes inclusivos y Acerca de)
│   │   │   │   └── viewmodel/AciertaProViewModel.kt (Gestión de estado, cronómetro reactivo de bolsa, navegación y persistencia en memoria)
│   │   └── res/
│   │       ├── drawable/ (ic_launcher_background.xml, ic_launcher_foreground.xml con logo de Acierta Pro)
│   │       └── mipmap-*/ (Iconos rasterizados generados para todas las densidades de pantalla)
├── capacitor.config.ts (Configuración de Capacitor para integración móvil híbrida)
└── README.md
```

---

## 2. Rutas y Flujo de Navegación

| Ruta / Estado | Pantalla | Descripción |
|---|---|---|
| `splash` | Splash de inicio | Bienvenida con logotipo geométrico, transición automática o al tocar `stage`. |
| `inicio` | Inicio | Dashboard principal con tarjeta de reanudación de intento activo, acceso a nuevo examen, historial, respaldos y ajustes. Doble toque en botón Atrás para salir. |
| `configurar` | Configurar simulacro | Selección de Sesión 1 (135 preg + 1 tarea) o Sesión 2, modo de tiempo de bolsa (280 min) y semilla aleatoria. |
| `resumen` | Resumen previo | Verificación de los 5 módulos en orden correlativo y botón de inicio del examen. |
| `marco` | Marco del examen | Cabecera con cronómetro ("Restan hh:mm:ss"), botón de pausa/mapa a revisión previa, navegación anterior/siguiente y confirmación de módulo. |
| `pregunta_seleccion` | Pregunta de selección múltiple | Vista de preguntas para Lectura Crítica, Razonamiento Cuantitativo y Competencias Ciudadanas. |
| `comunicacion_escrita` | Comunicación Escrita | Consigna argumentativa sobre IA en selección laboral, editor de texto multitarea con contador de palabras. |
| `pregunta_ingles` | Pregunta de Inglés | Preguntas 91 a 135 con avisos, gramática y comprensión de lectura. |
| `revision_previa` | Revisión previa a entrega | Mapa táctil de preguntas con conteo de respondidas, marcadas y pendientes, botón de entrega o retorno al texto. |
| `resultados` | Resultados | Conteo de aciertos 112/135 (83.0%), tiempo total 3h 55m, acceso a revisión de preguntas, evaluación de ensayo y PDF. |
| `revision_preguntas` | Revisión de preguntas | Filtros por Todas / Acertadas / Erradas / Marcadas con respuestas correctas y justificación pedagógica. |
| `evaluacion_escrita` | Evaluación de Comunicación Escrita | Desglose por rúbrica de 3 dimensiones (Planteamiento, Organización, Lenguaje) totalizando 14/18 puntos (Nivel 3 Orientativo). |
| `resultados_modulo` | Resultados por módulo y competencia | Análisis granular de competencias evaluadas por módulo. |
| `vista_previa_informe` | Vista previa del informe (Móvil) | Simulación de reporte imprimible con identificación de intento y semilla de generación. |
| `exportar_pdf` | Exportar a PDF | Opciones para generar y compartir el documento local. |
| `historial` | Historial | Lista de 5 intentos guardados (incluyendo SIM-2026-2-88F4 y el intento pausado SIM-2026-S2-44C1). |
| `estadisticas` | Estadísticas | Métricas de ritmo (1.7 min/pregunta) y efectividad acumulada. |
| `respaldos` | Respaldos | Exportación e importación local JSON (2.4 MB de datos). |
| `ajustes` | Ajustes Inclusivos | Control de fuentes (16, 18, 21, 24 px), dislexia, alto contraste, movimiento y vibración háptica. |
| `acerca` | Acerca de y fuentes | Estructura Saber Pro 2026-2 y aviso explícito de no oficialidad. |

---

## 3. Puntos de Conexión del Backend (Fase 2)

La arquitectura desacopla estrictamente la interfaz de la persistencia mediante interfaces en `src/main/java/com/example/data/repository/ExamRepository.kt`:

1. **`ExamRepository`**:
   - `getQuestionsForSession(sessionNumber: Int): List<Question>`
   - `getEssayPrompt(): String`
   - *Conexión*: Reemplazar `InMemoryExamRepository` por un cliente Room SQLite o API local que descargue el banco de preguntas por semilla.

2. **`AttemptRepository`**:
   - `getAttempts(): List<ExamAttempt>`
   - `getAttemptById(id: String): ExamAttempt?`
   - `saveAttempt(attempt: ExamAttempt)`
   - `getPausedAttempt(): ExamAttempt?`
   - *Conexión*: Vincular con Room Database (`AttemptDao`) para persistencia física en base de datos SQLite encriptada en el almacenamiento privado del dispositivo.

3. **`SettingsRepository`**:
   - `getSettings(): InclusiveSettings`
   - `updateSettings(settings: InclusiveSettings)`
   - *Conexión*: Persistir en `DataStore<Preferences>` de Jetpack.

4. **`BackupService` / `PdfService`**:
   - Exportación local de archivo JSON a la carpeta `Documents/AciertaPro` y renderizado de PDF nativo con `android.graphics.pdf.PdfDocument`.

---

## 4. Pasos para Compilación y Generación de APK

### En este entorno Android:
El proyecto compila directamente con Gradle:
```bash
gradle assembleDebug
```
El APK se genera en `app/build/outputs/apk/debug/app-debug.apk`.

### En entorno Capacitor (si se exporta a proyecto Web):
1. Instalar dependencias: `npm install`
2. Construir la web: `npm run build`
3. Añadir plataforma Android: `npx cap add android`
4. Sincronizar activos: `npx cap sync`
5. Abrir en Android Studio: `npx cap open android`
6. Generar APK: Menú `Build` > `Build Bundle(s) / APK(s)` > `Build APK(s)`.
