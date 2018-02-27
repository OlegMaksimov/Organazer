/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { OrganazerTestModule } from '../../../test.module';
import { BalanceWheelDetailComponent } from '../../../../../../main/webapp/app/entities/balance-wheel/balance-wheel-detail.component';
import { BalanceWheelService } from '../../../../../../main/webapp/app/entities/balance-wheel/balance-wheel.service';
import { BalanceWheel } from '../../../../../../main/webapp/app/entities/balance-wheel/balance-wheel.model';

describe('Component Tests', () => {

    describe('BalanceWheel Management Detail Component', () => {
        let comp: BalanceWheelDetailComponent;
        let fixture: ComponentFixture<BalanceWheelDetailComponent>;
        let service: BalanceWheelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OrganazerTestModule],
                declarations: [BalanceWheelDetailComponent],
                providers: [
                    BalanceWheelService
                ]
            })
            .overrideTemplate(BalanceWheelDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BalanceWheelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceWheelService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BalanceWheel(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.balanceWheel).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
