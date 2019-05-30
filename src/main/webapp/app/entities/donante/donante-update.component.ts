import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDonante, Donante } from 'app/shared/model/donante.model';
import { DonanteService } from './donante.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { HospitalService } from 'app/entities/hospital';

@Component({
  selector: 'jhi-donante-update',
  templateUrl: './donante-update.component.html'
})
export class DonanteUpdateComponent implements OnInit {
  donante: IDonante;
  isSaving: boolean;

  hospitals: IHospital[];
  fechaNacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    apellido: [null, [Validators.required]],
    direccion: [null, [Validators.required]],
    telefono: [null, [Validators.required]],
    email: [],
    fechaNacimiento: [null, [Validators.required]],
    sexo: [null, [Validators.required]],
    grupoSanguineo: [null, [Validators.required]],
    factorSanguineo: [null, [Validators.required]],
    activo: [],
    hospital: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected donanteService: DonanteService,
    protected hospitalService: HospitalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ donante }) => {
      this.updateForm(donante);
      this.donante = donante;
    });
    this.hospitalService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IHospital[]>) => mayBeOk.ok),
        map((response: HttpResponse<IHospital[]>) => response.body)
      )
      .subscribe((res: IHospital[]) => (this.hospitals = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(donante: IDonante) {
    this.editForm.patchValue({
      id: donante.id,
      nombre: donante.nombre,
      apellido: donante.apellido,
      direccion: donante.direccion,
      telefono: donante.telefono,
      email: donante.email,
      fechaNacimiento: donante.fechaNacimiento,
      sexo: donante.sexo,
      grupoSanguineo: donante.grupoSanguineo,
      factorSanguineo: donante.factorSanguineo,
      activo: donante.activo,
      hospital: donante.hospital
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const donante = this.createFromForm();
    if (donante.id !== undefined) {
      this.subscribeToSaveResponse(this.donanteService.update(donante));
    } else {
      this.subscribeToSaveResponse(this.donanteService.create(donante));
    }
  }

  private createFromForm(): IDonante {
    const entity = {
      ...new Donante(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellido: this.editForm.get(['apellido']).value,
      direccion: this.editForm.get(['direccion']).value,
      telefono: this.editForm.get(['telefono']).value,
      email: this.editForm.get(['email']).value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento']).value,
      sexo: this.editForm.get(['sexo']).value,
      grupoSanguineo: this.editForm.get(['grupoSanguineo']).value,
      factorSanguineo: this.editForm.get(['factorSanguineo']).value,
      activo: this.editForm.get(['activo']).value,
      hospital: this.editForm.get(['hospital']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonante>>) {
    result.subscribe((res: HttpResponse<IDonante>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
