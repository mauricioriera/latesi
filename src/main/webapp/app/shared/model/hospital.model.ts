export interface IHospital {
  id?: number;
  nombre?: string;
  direccion?: string;
  email?: string;
  telefono?: string;
}

export class Hospital implements IHospital {
  constructor(public id?: number, public nombre?: string, public direccion?: string, public email?: string, public telefono?: string) {}
}
