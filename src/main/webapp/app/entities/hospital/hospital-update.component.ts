import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IHospital, Hospital } from 'app/shared/model/hospital.model';
import { HospitalService } from './hospital.service';

@Component({
  selector: 'jhi-hospital-update',
  templateUrl: './hospital-update.component.html'
})
export class HospitalUpdateComponent implements OnInit {
  hospital: IHospital;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    direccion: [null, [Validators.required]],
    email: [],
    telefono: [null, [Validators.required]]
  });

  constructor(protected hospitalService: HospitalService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ hospital }) => {
      this.updateForm(hospital);
      this.hospital = hospital;
    });
  }

  updateForm(hospital: IHospital) {
    this.editForm.patchValue({
      id: hospital.id,
      nombre: hospital.nombre,
      direccion: hospital.direccion,
      email: hospital.email,
      telefono: hospital.telefono
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const hospital = this.createFromForm();
    if (hospital.id !== undefined) {
      this.subscribeToSaveResponse(this.hospitalService.update(hospital));
    } else {
      this.subscribeToSaveResponse(this.hospitalService.create(hospital));
    }
  }

  private createFromForm(): IHospital {
    const entity = {
      ...new Hospital(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      direccion: this.editForm.get(['direccion']).value,
      email: this.editForm.get(['email']).value,
      telefono: this.editForm.get(['telefono']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHospital>>) {
    result.subscribe((res: HttpResponse<IHospital>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
