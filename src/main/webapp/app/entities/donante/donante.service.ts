import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDonante } from 'app/shared/model/donante.model';

type EntityResponseType = HttpResponse<IDonante>;
type EntityArrayResponseType = HttpResponse<IDonante[]>;

@Injectable({ providedIn: 'root' })
export class DonanteService {
  public resourceUrl = SERVER_API_URL + 'api/donantes';

  constructor(protected http: HttpClient) {}

  create(donante: IDonante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donante);
    return this.http
      .post<IDonante>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(donante: IDonante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donante);
    return this.http
      .put<IDonante>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDonante>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDonante[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(donante: IDonante): IDonante {
    const copy: IDonante = Object.assign({}, donante, {
      fechaNacimiento:
        donante.fechaNacimiento != null && donante.fechaNacimiento.isValid() ? donante.fechaNacimiento.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaNacimiento = res.body.fechaNacimiento != null ? moment(res.body.fechaNacimiento) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((donante: IDonante) => {
        donante.fechaNacimiento = donante.fechaNacimiento != null ? moment(donante.fechaNacimiento) : null;
      });
    }
    return res;
  }
}
