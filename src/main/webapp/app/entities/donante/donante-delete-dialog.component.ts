import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDonante } from 'app/shared/model/donante.model';
import { DonanteService } from './donante.service';

@Component({
  selector: 'jhi-donante-delete-dialog',
  templateUrl: './donante-delete-dialog.component.html'
})
export class DonanteDeleteDialogComponent {
  donante: IDonante;

  constructor(protected donanteService: DonanteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.donanteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'donanteListModification',
        content: 'Deleted an donante'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-donante-delete-popup',
  template: ''
})
export class DonanteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ donante }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DonanteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.donante = donante;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/donante', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/donante', { outlets: { popup: null } }]);
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
