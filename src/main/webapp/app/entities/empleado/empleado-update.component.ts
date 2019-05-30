import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IEmpleado, Empleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from './empleado.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { HospitalService } from 'app/entities/hospital';

@Component({
  selector: 'jhi-empleado-update',
  templateUrl: './empleado-update.component.html'
})
export class EmpleadoUpdateComponent implements OnInit {
  empleado: IEmpleado;
  isSaving: boolean;

  hospitals: IHospital[];
  fechaNacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    telefono: [null, [Validators.required]],
    fechaNacimiento: [null, [Validators.required]],
    numeroLegajo: [null, [Validators.required]],
    hospital: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected empleadoService: EmpleadoService,
    protected hospitalService: HospitalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ empleado }) => {
      this.updateForm(empleado);
      this.empleado = empleado;
    });
    this.hospitalService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IHospital[]>) => mayBeOk.ok),
        map((response: HttpResponse<IHospital[]>) => response.body)
      )
      .subscribe((res: IHospital[]) => (this.hospitals = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(empleado: IEmpleado) {
    this.editForm.patchValue({
      id: empleado.id,
      telefono: empleado.telefono,
      fechaNacimiento: empleado.fechaNacimiento,
      numeroLegajo: empleado.numeroLegajo,
      hospital: empleado.hospital
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const empleado = this.createFromForm();
    if (empleado.id !== undefined) {
      this.subscribeToSaveResponse(this.empleadoService.update(empleado));
    } else {
      this.subscribeToSaveResponse(this.empleadoService.create(empleado));
    }
  }

  private createFromForm(): IEmpleado {
    const entity = {
      ...new Empleado(),
      id: this.editForm.get(['id']).value,
      telefono: this.editForm.get(['telefono']).value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento']).value,
      numeroLegajo: this.editForm.get(['numeroLegajo']).value,
      hospital: this.editForm.get(['hospital']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpleado>>) {
    result.subscribe((res: HttpResponse<IEmpleado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackHospitalById(index: number, item: IHospital) {
    return item.id;
  }
}
