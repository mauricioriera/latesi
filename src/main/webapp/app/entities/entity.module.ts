import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'donante',
        loadChildren: './donante/donante.module#DonarDonanteModule'
      },
      {
        path: 'hospital',
        loadChildren: './hospital/hospital.module#DonarHospitalModule'
      },
      {
        path: 'empleado',
        loadChildren: './empleado/empleado.module#DonarEmpleadoModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DonarEntityModule {}
