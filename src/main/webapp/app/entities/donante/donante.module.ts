import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DonarSharedModule } from 'app/shared';
import {
  DonanteComponent,
  DonanteDetailComponent,
  DonanteUpdateComponent,
  DonanteDeletePopupComponent,
  DonanteDeleteDialogComponent,
  donanteRoute,
  donantePopupRoute
} from './';

const ENTITY_STATES = [...donanteRoute, ...donantePopupRoute];

@NgModule({
  imports: [DonarSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DonanteComponent,
    DonanteDetailComponent,
    DonanteUpdateComponent,
    DonanteDeleteDialogComponent,
    DonanteDeletePopupComponent
  ],
  entryComponents: [DonanteComponent, DonanteUpdateComponent, DonanteDeleteDialogComponent, DonanteDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DonarDonanteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
