import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHospital } from 'app/shared/model/hospital.model';
import { HospitalService } from './hospital.service';

@Component({
  selector: 'jhi-hospital-delete-dialog',
  templateUrl: './hospital-delete-dialog.component.html'
})
export class HospitalDeleteDialogComponent {
  hospital: IHospital;

  constructor(protected hospitalService: HospitalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.hospitalService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'hospitalListModification',
        content: 'Deleted an hospital'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-hospital-delete-popup',
  template: ''
})
export class HospitalDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ hospital }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(HospitalDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.hospital = hospital;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/hospital', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/hospital', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
