package com.pupr.domain.bidangtahunactive;

import java.util.Optional;

public interface BidangTahunActiveRepository {

    Optional<BidangTahunActive> findByBidangName(String bidangName);
}
