/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OrganazerTestModule } from '../../../test.module';
import { BalanceWheelDialogComponent } from '../../../../../../main/webapp/app/entities/balance-wheel/balance-wheel-dialog.component';
import { BalanceWheelService } from '../../../../../../main/webapp/app/entities/balance-wheel/balance-wheel.service';
import { BalanceWheel } from '../../../../../../main/webapp/app/entities/balance-wheel/balance-wheel.model';
import { CategoryService } from '../../../../../../main/webapp/app/entities/category';

describe('Component Tests', () => {

    describe('BalanceWheel Management Dialog Component', () => {
        let comp: BalanceWheelDialogComponent;
        let fixture: ComponentFixture<BalanceWheelDialogComponent>;
        let service: BalanceWheelService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OrganazerTestModule],
                declarations: [BalanceWheelDialogComponent],
                providers: [
                    CategoryService,
                    BalanceWheelService
                ]
            })
            .overrideTemplate(BalanceWheelDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BalanceWheelDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceWheelService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BalanceWheel(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.balanceWheel = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'balanceWheelListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BalanceWheel();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.balanceWheel = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'balanceWheelListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
