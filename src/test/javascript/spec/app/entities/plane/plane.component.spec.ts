/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrganazerTestModule } from '../../../test.module';
import { PlaneComponent } from '../../../../../../main/webapp/app/entities/plane/plane.component';
import { PlaneService } from '../../../../../../main/webapp/app/entities/plane/plane.service';
import { Plane } from '../../../../../../main/webapp/app/entities/plane/plane.model';

describe('Component Tests', () => {

    describe('Plane Management Component', () => {
        let comp: PlaneComponent;
        let fixture: ComponentFixture<PlaneComponent>;
        let service: PlaneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OrganazerTestModule],
                declarations: [PlaneComponent],
                providers: [
                    PlaneService
                ]
            })
            .overrideTemplate(PlaneComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlaneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlaneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Plane(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.planes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
