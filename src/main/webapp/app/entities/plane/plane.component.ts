import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Plane } from './plane.model';
import { PlaneService } from './plane.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-plane',
    templateUrl: './plane.component.html'
})
export class PlaneComponent implements OnInit, OnDestroy {
planes: Plane[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private planeService: PlaneService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.planeService.query().subscribe(
            (res: HttpResponse<Plane[]>) => {
                this.planes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlanes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Plane) {
        return item.id;
    }
    registerChangeInPlanes() {
        this.eventSubscriber = this.eventManager.subscribe('planeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
