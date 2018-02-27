import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BalanceWheel } from './balance-wheel.model';
import { BalanceWheelService } from './balance-wheel.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-balance-wheel',
    templateUrl: './balance-wheel.component.html'
})
export class BalanceWheelComponent implements OnInit, OnDestroy {
balanceWheels: BalanceWheel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private balanceWheelService: BalanceWheelService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.balanceWheelService.query().subscribe(
            (res: HttpResponse<BalanceWheel[]>) => {
                this.balanceWheels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBalanceWheels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BalanceWheel) {
        return item.id;
    }
    registerChangeInBalanceWheels() {
        this.eventSubscriber = this.eventManager.subscribe('balanceWheelListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
