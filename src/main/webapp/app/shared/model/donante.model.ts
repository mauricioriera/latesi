import { Moment } from 'moment';
import { IHospital } from 'app/shared/model/hospital.model';

export interface IDonante {
  id?: number;
  nombre?: string;
  apellido?: string;
  direccion?: string;
  telefono?: string;
  email?: string;
  fechaNacimiento?: Moment;
  sexo?: string;
  grupoSanguineo?: string;
  factorSanguineo?: string;
  activo?: boolean;
  hospital?: IHospital;
}

export class Donante implements IDonante {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellido?: string,
    public direccion?: string,
    public telefono?: string,
    public email?: string,
    public fechaNacimiento?: Moment,
    public sexo?: string,
    public grupoSanguineo?: string,
    public factorSanguineo?: string,
    public activo?: boolean,
    public hospital?: IHospital
  ) {
    this.activo = this.activo || false;
  }
}
