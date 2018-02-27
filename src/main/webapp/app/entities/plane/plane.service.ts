import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Plane } from './plane.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Plane>;

@Injectable()
export class PlaneService {

    private resourceUrl =  SERVER_API_URL + 'api/planes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(plane: Plane): Observable<EntityResponseType> {
        const copy = this.convert(plane);
        return this.http.post<Plane>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(plane: Plane): Observable<EntityResponseType> {
        const copy = this.convert(plane);
        return this.http.put<Plane>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Plane>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Plane[]>> {
        const options = createRequestOption(req);
        return this.http.get<Plane[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Plane[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Plane = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Plane[]>): HttpResponse<Plane[]> {
        const jsonResponse: Plane[] = res.body;
        const body: Plane[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Plane.
     */
    private convertItemFromServer(plane: Plane): Plane {
        const copy: Plane = Object.assign({}, plane);
        copy.dateStart = this.dateUtils
            .convertDateTimeFromServer(plane.dateStart);
        copy.dateEnd = this.dateUtils
            .convertDateTimeFromServer(plane.dateEnd);
        return copy;
    }

    /**
     * Convert a Plane to a JSON which can be sent to the server.
     */
    private convert(plane: Plane): Plane {
        const copy: Plane = Object.assign({}, plane);

        copy.dateStart = this.dateUtils.toDate(plane.dateStart);

        copy.dateEnd = this.dateUtils.toDate(plane.dateEnd);
        return copy;
    }
}
