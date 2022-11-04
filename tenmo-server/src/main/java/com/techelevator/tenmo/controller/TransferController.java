package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@PreAuthorize("permitAll()")
@RestController
public class TransferController {
    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;}


    @RequestMapping(path = "mytransfers/{accountId}", method = RequestMethod.GET)
    public List<Transfer> listTransfer(@PathVariable int accountId) { return transferDao.getAllTransfers();}

    @RequestMapping(path = "transfers/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int transferId) {
        return transferDao.getTransferByTransferId(transferId);
    }

    @RequestMapping(path = "sendMoney/{accountTo}/{accountFrom}/{amount}", method = RequestMethod.PUT)
    public void sendMoney(@PathVariable int accountTo, @PathVariable int accountFrom, @Valid @PathVariable BigDecimal amount) {
        transferDao.sendMoney(accountTo, accountFrom, amount);

    }
}

