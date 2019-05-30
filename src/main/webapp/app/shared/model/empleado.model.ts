import { Moment } from 'moment';
import { IHospital } from 'app/shared/model/hospital.model';

export interface IEmpleado {
  id?: number;
  telefono?: string;
  fechaNacimiento?: Moment;
  numeroLegajo?: number;
  hospital?: IHospital;
}

export class Empleado implements IEmpleado {
  constructor(
    public id?: number,
    public telefono?: string,
    public fechaNacimiento?: Moment,
    public numeroLegajo?: number,
    public hospital?: IHospital
  ) {}
}
