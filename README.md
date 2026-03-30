Sistema de gestión de pedidos REM (en desarrollo)

Aplicación de escritorio orientada a la gestión y seguimiento de pedidos de lentes, con el objetivo de unificar distintas fuentes de datos y automatizar la integración con sistemas de fabricación (REM) y facturación (OPUS).

Estado

Proyecto en desarrollo. Actualmente se encuentra en etapa de diseño e implementación inicial de arquitectura y flujo de datos.

Descripción

El objetivo de este sistema es centralizar la gestión de pedidos provenientes de múltiples planillas y fuentes, unificando la información mediante una API y automatizando el flujo desde la carga del pedido hasta su fabricación y facturación.

Se busca reducir tareas manuales, evitar inconsistencias entre sistemas y mejorar la trazabilidad de cada pedido a lo largo de todo el proceso productivo.

Objetivos principales
Unificar pedidos provenientes de distintas planillas (por ejemplo, Google Drive)
Exponer una API interna para centralizar la información
Enviar automáticamente los pedidos al sistema del torno REM
Consultar la base de datos del sistema REM para detectar:
si el pedido fue iniciado
qué tipo de corte se realizó (back y/o front)
Actualizar el estado del pedido en función del avance de fabricación
Integrar la facturación cargando los datos directamente en el sistema OPUS
Funcionalidades previstas
Importación de pedidos desde múltiples fuentes
Normalización y validación de datos
Gestión de estados del pedido (pendiente, en proceso, terminado, facturado)
Separación por sectores (fábrica, locales, otras ópticas)
Integración con sistema REM
Integración con sistema de facturación (OPUS)
Interfaz para visualización y gestión de pedidos
Arquitectura (propuesta)

El sistema se plantea con una arquitectura modular:

model: entidades de dominio (Pedido, Cliente, Lente, etc.)
service: lógica de negocio y procesamiento de pedidos
controller: coordinación entre UI y servicios
repository: acceso a datos (archivos y/o base de datos)
integration:
integración con REM
integración con OPUS
integración con fuentes externas (planillas)
ui: interfaz de usuario (JavaFX)
Tecnologías previstas
Java (aplicación principal)
API REST para integración de datos
Automatización para interacción con sistemas externos
Posible uso de base de datos local o remota para persistencia
Notas
La integración con REM y OPUS depende de las interfaces disponibles de cada sistema.
Parte del proyecto implica resolver la sincronización entre datos externos y estados internos.
Se prioriza mantener consistencia y trazabilidad de cada pedido.
Próximos pasos
Definición completa del modelo de datos
Implementación de la capa de importación de pedidos
Diseño de la API interna
Primera integración con sistema REM
Manejo de estados y sincronización
Integración con facturación (OPUS)
Enfoque

El proyecto está orientado a resolver un problema real de integración entre sistemas, con foco en:

centralización de información
automatización de procesos
reducción de errores manuales
trazabilidad de pedidos
