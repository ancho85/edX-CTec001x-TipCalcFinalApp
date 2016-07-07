# edX-CTec001x-TipCalc
Calculadora de propinas Final APP.

## TODO:
- [x] Vista normal (anterior) intacta.
- [ ] Firebase con Facebook login. Guardar propinas por cada usuario con acceso.
- [ ] Vista premium acceso vía menú a una actividad de login de facebook.
- [ ] Vista premium activado una vez logueado a facebook (y firebase)
- [ ] Vista premium desactivado una vez deslogueado de facebook.
- [ ] Coordinator Layout para agrupar ambas vistas
- [ ] TabLayout para poner la sección de ExtraStaff
- [ ] HorizontalScrollView + LinearLayout + button rounded corner background para selección de continente
- [ ] HorizontalScrollView + LinearLayout + button rounded corner background para selección de clasificación de servicio
- [ ] ViewPager que incluirá CardViews del histórico de propinas
- [ ] Cada propina calculada guardará la ubicación donde se hizo el cálculo
- [ ] Cada propina podrá ser compartida en facebook app y en el messenger
- [ ] Retrofit para obtener cotización de moneda paraguaya desde api
- [ ] FloatingButton para abrir otra actividad/fragment y visualizar la cotización del día
- [ ] Total food
- [ ] Total drinks
- [ ] Total general
- [ ] Unit tests?

## EXTRA:
1. Continentes:
    * Africa/Australia/Sudamérica: %10 por defecto, permite menos
    * Norte América: 15% defecto, no permitir menos (de forma manual con botón decrease)
    * Asia: Snackbar de advertencia sobre propina ofensiva
    * Europa: 20% por defecto, permite menos
2. Clasificación de servicio:
    * Promedio: % default de cada continente
    * Bueno: 20%
    * Excelente: 25%
    * Extraordinario: 30%
3. Sección ExtraStaff:
    * Guardaropa: 1 U$S más al total general
    * Coche: 2 U$S más al total general
    * Sumiller: 15% sobre el total drinks