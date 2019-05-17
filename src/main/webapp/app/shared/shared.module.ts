import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DonarSharedLibsModule, DonarSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [DonarSharedLibsModule, DonarSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [DonarSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DonarSharedModule {
  static forRoot() {
    return {
      ngModule: DonarSharedModule
    };
  }
}
