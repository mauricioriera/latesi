import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHospital } from 'app/shared/model/hospital.model';
import { AccountService } from 'app/core';
import { HospitalService } from './hospital.service';

@Component({
  selector: 'jhi-hospital',
  templateUrl: './hospital.component.html'
})
export class HospitalComponent implements OnInit, OnDestroy {
  hospitals: IHospital[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected hospitalService: HospitalService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.hospitalService
      .query()
      .pipe(
        filter((res: HttpResponse<IHospital[]>) => res.ok),
        map((res: HttpResponse<IHospital[]>) => res.body)
      )
      .subscribe(
        (res: IHospital[]) => {
          this.hospitals = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHospitals();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHospital) {
    return item.id;
  }

  registerChangeInHospitals() {
    this.eventSubscriber = this.eventManager.subscribe('hospitalListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
