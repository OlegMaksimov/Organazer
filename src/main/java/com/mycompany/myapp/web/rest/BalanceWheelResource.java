package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BalanceWheel;

import com.mycompany.myapp.repository.BalanceWheelRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BalanceWheel.
 */
@RestController
@RequestMapping("/api")
public class BalanceWheelResource {

    private final Logger log = LoggerFactory.getLogger(BalanceWheelResource.class);

    private static final String ENTITY_NAME = "balanceWheel";

    private final BalanceWheelRepository balanceWheelRepository;

    public BalanceWheelResource(BalanceWheelRepository balanceWheelRepository) {
        this.balanceWheelRepository = balanceWheelRepository;
    }

    /**
     * POST  /balance-wheels : Create a new balanceWheel.
     *
     * @param balanceWheel the balanceWheel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new balanceWheel, or with status 400 (Bad Request) if the balanceWheel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/balance-wheels")
    @Timed
    public ResponseEntity<BalanceWheel> createBalanceWheel(@RequestBody BalanceWheel balanceWheel) throws URISyntaxException {
        log.debug("REST request to save BalanceWheel : {}", balanceWheel);
        if (balanceWheel.getId() != null) {
            throw new BadRequestAlertException("A new balanceWheel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BalanceWheel result = balanceWheelRepository.save(balanceWheel);
        return ResponseEntity.created(new URI("/api/balance-wheels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /balance-wheels : Updates an existing balanceWheel.
     *
     * @param balanceWheel the balanceWheel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated balanceWheel,
     * or with status 400 (Bad Request) if the balanceWheel is not valid,
     * or with status 500 (Internal Server Error) if the balanceWheel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/balance-wheels")
    @Timed
    public ResponseEntity<BalanceWheel> updateBalanceWheel(@RequestBody BalanceWheel balanceWheel) throws URISyntaxException {
        log.debug("REST request to update BalanceWheel : {}", balanceWheel);
        if (balanceWheel.getId() == null) {
            return createBalanceWheel(balanceWheel);
        }
        BalanceWheel result = balanceWheelRepository.save(balanceWheel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, balanceWheel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /balance-wheels : get all the balanceWheels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of balanceWheels in body
     */
    @GetMapping("/balance-wheels")
    @Timed
    public List<BalanceWheel> getAllBalanceWheels() {
        log.debug("REST request to get all BalanceWheels");
        return balanceWheelRepository.findAll();
        }

    /**
     * GET  /balance-wheels/:id : get the "id" balanceWheel.
     *
     * @param id the id of the balanceWheel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the balanceWheel, or with status 404 (Not Found)
     */
    @GetMapping("/balance-wheels/{id}")
    @Timed
    public ResponseEntity<BalanceWheel> getBalanceWheel(@PathVariable Long id) {
        log.debug("REST request to get BalanceWheel : {}", id);
        BalanceWheel balanceWheel = balanceWheelRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(balanceWheel));
    }

    /**
     * DELETE  /balance-wheels/:id : delete the "id" balanceWheel.
     *
     * @param id the id of the balanceWheel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/balance-wheels/{id}")
    @Timed
    public ResponseEntity<Void> deleteBalanceWheel(@PathVariable Long id) {
        log.debug("REST request to delete BalanceWheel : {}", id);
        balanceWheelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
