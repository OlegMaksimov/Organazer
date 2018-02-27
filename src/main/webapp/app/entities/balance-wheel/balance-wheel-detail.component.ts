import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BalanceWheel } from './balance-wheel.model';
import { BalanceWheelService } from './balance-wheel.service';

@Component({
    selector: 'jhi-balance-wheel-detail',
    templateUrl: './balance-wheel-detail.component.html'
})
export class BalanceWheelDetailComponent implements OnInit, OnDestroy {

    balanceWheel: BalanceWheel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private balanceWheelService: BalanceWheelService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBalanceWheels();
    }

    load(id) {
        this.balanceWheelService.find(id)
            .subscribe((balanceWheelResponse: HttpResponse<BalanceWheel>) => {
                this.balanceWheel = balanceWheelResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBalanceWheels() {
        this.eventSubscriber = this.eventManager.subscribe(
            'balanceWheelListModification',
            (response) => this.load(this.balanceWheel.id)
        );
    }
}
