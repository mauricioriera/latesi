import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDonante } from 'app/shared/model/donante.model';
import { AccountService } from 'app/core';
import { DonanteService } from './donante.service';

@Component({
  selector: 'jhi-donante',
  templateUrl: './donante.component.html'
})
export class DonanteComponent implements OnInit, OnDestroy {
  donantes: IDonante[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected donanteService: DonanteService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.donanteService
      .query()
      .pipe(
        filter((res: HttpResponse<IDonante[]>) => res.ok),
        map((res: HttpResponse<IDonante[]>) => res.body)
      )
      .subscribe(
        (res: IDonante[]) => {
          this.donantes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDonantes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDonante) {
    return item.id;
  }

  registerChangeInDonantes() {
    this.eventSubscriber = this.eventManager.subscribe('donanteListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
