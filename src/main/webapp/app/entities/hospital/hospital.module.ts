import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DonarSharedModule } from 'app/shared';
import {
  HospitalComponent,
  HospitalDetailComponent,
  HospitalUpdateComponent,
  HospitalDeletePopupComponent,
  HospitalDeleteDialogComponent,
  hospitalRoute,
  hospitalPopupRoute
} from './';

const ENTITY_STATES = [...hospitalRoute, ...hospitalPopupRoute];

@NgModule({
  imports: [DonarSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    HospitalComponent,
    HospitalDetailComponent,
    HospitalUpdateComponent,
    HospitalDeleteDialogComponent,
    HospitalDeletePopupComponent
  ],
  entryComponents: [HospitalComponent, HospitalUpdateComponent, HospitalDeleteDialogComponent, HospitalDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DonarHospitalModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
