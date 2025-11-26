package com.back.domain.reservation.scheduler.job;

import com.back.domain.reservation.common.ReservationStatus;
import com.back.domain.reservation.entity.Reservation;
import com.back.domain.reservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j

public class ReservationStatusJob implements Job {
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            updateClaimingStatus();
            updatePendingRefundStatus();
        } catch (Exception e) {
            log.error("예약 상태 자동 업데이트 중 오류 발생", e);
            throw new JobExecutionException(e);
        }
    }

    private void updateClaimingStatus() {
        List<Reservation> reservations = reservationRepository.findByStatus(ReservationStatus.CLAIMING);

        reservations.forEach(reservation -> {
            reservation.changeStatus(ReservationStatus.CLAIM_COMPLETED);
            reservationRepository.save(reservation);
        });

        log.info("CLAIMING → CLAIM_COMPLETED 상태 변경 완료 - 처리 건수: {}", reservations.size());
    }

    private void updatePendingRefundStatus() {
        List<Reservation> reservations = reservationRepository.findByStatus(ReservationStatus.PENDING_REFUND);

        reservations.forEach(reservation -> {
            reservation.changeStatus(ReservationStatus.REFUND_COMPLETED);
            reservationRepository.save(reservation);
        });

        log.info("PENDING_REFUND → REFUND_COMPLETED 상태 변경 완료 - 처리 건수: {}", reservations.size());
    }
}
