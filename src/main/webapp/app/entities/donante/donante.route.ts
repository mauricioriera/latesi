import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Donante } from 'app/shared/model/donante.model';
import { DonanteService } from './donante.service';
import { DonanteComponent } from './donante.component';
import { DonanteDetailComponent } from './donante-detail.component';
import { DonanteUpdateComponent } from './donante-update.component';
import { DonanteDeletePopupComponent } from './donante-delete-dialog.component';
import { IDonante } from 'app/shared/model/donante.model';

@Injectable({ providedIn: 'root' })
export class DonanteResolve implements Resolve<IDonante> {
  constructor(private service: DonanteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDonante> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Donante>) => response.ok),
        map((donante: HttpResponse<Donante>) => donante.body)
      );
    }
    return of(new Donante());
  }
}

export const donanteRoute: Routes = [
  {
    path: '',
    component: DonanteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'donarApp.donante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DonanteDetailComponent,
    resolve: {
      donante: DonanteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'donarApp.donante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DonanteUpdateComponent,
    resolve: {
      donante: DonanteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'donarApp.donante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DonanteUpdateComponent,
    resolve: {
      donante: DonanteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'donarApp.donante.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const donantePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DonanteDeletePopupComponent,
    resolve: {
      donante: DonanteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'donarApp.donante.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
