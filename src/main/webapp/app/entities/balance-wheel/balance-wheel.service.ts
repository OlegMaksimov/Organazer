import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { BalanceWheel } from './balance-wheel.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<BalanceWheel>;

@Injectable()
export class BalanceWheelService {

    private resourceUrl =  SERVER_API_URL + 'api/balance-wheels';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(balanceWheel: BalanceWheel): Observable<EntityResponseType> {
        const copy = this.convert(balanceWheel);
        return this.http.post<BalanceWheel>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(balanceWheel: BalanceWheel): Observable<EntityResponseType> {
        const copy = this.convert(balanceWheel);
        return this.http.put<BalanceWheel>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<BalanceWheel>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BalanceWheel[]>> {
        const options = createRequestOption(req);
        return this.http.get<BalanceWheel[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BalanceWheel[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BalanceWheel = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<BalanceWheel[]>): HttpResponse<BalanceWheel[]> {
        const jsonResponse: BalanceWheel[] = res.body;
        const body: BalanceWheel[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to BalanceWheel.
     */
    private convertItemFromServer(balanceWheel: BalanceWheel): BalanceWheel {
        const copy: BalanceWheel = Object.assign({}, balanceWheel);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(balanceWheel.date);
        return copy;
    }

    /**
     * Convert a BalanceWheel to a JSON which can be sent to the server.
     */
    private convert(balanceWheel: BalanceWheel): BalanceWheel {
        const copy: BalanceWheel = Object.assign({}, balanceWheel);

        copy.date = this.dateUtils.toDate(balanceWheel.date);
        return copy;
    }
}
