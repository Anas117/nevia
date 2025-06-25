package com.pds.neviabooking.repositories;

import com.pds.neviabooking.dto.RoomAvailableDto;
import com.pds.neviabooking.entities.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;

public interface RoomRepository extends CrudRepository<RoomEntity, Integer> {

    @Query(value = """
            Select new com.pds.neviabooking.dto.RoomAvailableDto(r.idRoom, r.name) from RoomEntity r
            where r.idRoom not in (Select b.idRoom from BookingEntity b
                where b.startTime <= :startTime and b.endTime >= :startTime)
            and r.idRoom not in (Select b.idRoom from BookingEntity b
                where b.startTime >= :startTime and b.startTime <= :endTime)
            and r.idRoom not in (Select b.idRoom from BookingEntity b
                where b.endTime >= :startTime and b.endTime <= :endTime)
            """)
    Page<RoomAvailableDto> selectAvailableRoom(Timestamp startTime, Timestamp endTime, Pageable pageable);

}
