import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Hospital } from 'app/shared/model/hospital.model';
import { HospitalService } from './hospital.service';
import { HospitalComponent } from './hospital.component';
import { HospitalDetailComponent } from './hospital-detail.component';
import { HospitalUpdateComponent } from './hospital-update.component';
import { HospitalDeletePopupComponent } from './hospital-delete-dialog.component';
import { IHospital } from 'app/shared/model/hospital.model';

@Injectable({ providedIn: 'root' })
export class HospitalResolve implements Resolve<IHospital> {
  constructor(private service: HospitalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHospital> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Hospital>) => response.ok),
        map((hospital: HttpResponse<Hospital>) => hospital.body)
      );
    }
    return of(new Hospital());
  }
}

export const hospitalRoute: Routes = [
  {
    path: '',
    component: HospitalComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'donarApp.hospital.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HospitalDetailComponent,
    resolve: {
      hospital: HospitalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'donarApp.hospital.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HospitalUpdateComponent,
    resolve: {
      hospital: HospitalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'donarApp.hospital.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HospitalUpdateComponent,
    resolve: {
      hospital: HospitalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'donarApp.hospital.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const hospitalPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: HospitalDeletePopupComponent,
    resolve: {
      hospital: HospitalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'donarApp.hospital.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
