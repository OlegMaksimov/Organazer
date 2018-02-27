import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Plane } from './plane.model';
import { PlaneService } from './plane.service';

@Component({
    selector: 'jhi-plane-detail',
    templateUrl: './plane-detail.component.html'
})
export class PlaneDetailComponent implements OnInit, OnDestroy {

    plane: Plane;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private planeService: PlaneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlanes();
    }

    load(id) {
        this.planeService.find(id)
            .subscribe((planeResponse: HttpResponse<Plane>) => {
                this.plane = planeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlanes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'planeListModification',
            (response) => this.load(this.plane.id)
        );
    }
}
