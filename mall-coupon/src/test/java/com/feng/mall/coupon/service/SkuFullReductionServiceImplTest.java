package com.feng.mall.coupon.service;

import com.feng.common.to.MemberPrice;
import com.feng.common.to.SkuReductionTo;
import com.feng.mall.coupon.dao.SkuFullReductionDao;
import com.feng.mall.coupon.entity.SkuFullReductionEntity;
import com.feng.mall.coupon.entity.SkuLadderEntity;
import com.feng.mall.coupon.service.impl.SkuFullReductionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkuFullReductionServiceImplTest {

    @Mock
    private SkuLadderService skuLadderService;

    @Mock
    private MemberPriceService memberPriceService;

    @Mock
    private SkuFullReductionDao skuFullReductionDao;

    private SkuFullReductionServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new SkuFullReductionServiceImpl();
        ReflectionTestUtils.setField(service, "skuLadderService", skuLadderService);
        ReflectionTestUtils.setField(service, "memberPriceService", memberPriceService);
        // ServiceImpl requires baseMapper
        ReflectionTestUtils.setField(service, "baseMapper", skuFullReductionDao);
    }

    // ── null memberPrice — previously caused NPE ──────────────────────────────

    @Test
    void saveSkuReduction_nullMemberPrice_noException() {
        SkuReductionTo to = reductionTo(0, BigDecimal.ZERO, null);

        assertThatNoException().isThrownBy(() -> service.saveSkuReduction(to));
        verify(memberPriceService, never()).saveBatch(anyList());
    }

    @Test
    void saveSkuReduction_emptyMemberPrice_noException() {
        SkuReductionTo to = reductionTo(0, BigDecimal.ZERO, List.of());

        assertThatNoException().isThrownBy(() -> service.saveSkuReduction(to));
        verify(memberPriceService, never()).saveBatch(anyList());
    }

    // ── zero fullCount — ladder should NOT be saved ───────────────────────────

    @Test
    void saveSkuReduction_zeroFullCount_ladderNotSaved() {
        SkuReductionTo to = reductionTo(0, new BigDecimal("100"), List.of());

        service.saveSkuReduction(to);

        verify(skuLadderService, never()).save(any(SkuLadderEntity.class));
    }

    @Test
    void saveSkuReduction_positiveFullCount_ladderSaved() {
        SkuReductionTo to = reductionTo(2, new BigDecimal("100"), List.of());
        to.setDiscount(new BigDecimal("0.9"));
        to.setCountStatus(1);

        service.saveSkuReduction(to);

        ArgumentCaptor<SkuLadderEntity> captor = ArgumentCaptor.forClass(SkuLadderEntity.class);
        verify(skuLadderService).save(captor.capture());
        SkuLadderEntity saved = captor.getValue();
        assert saved.getFullCount() == 2;
        assert saved.getSkuId().equals(100L);
    }

    // ── zero fullPrice — reduction entity should NOT be saved ─────────────────

    @Test
    void saveSkuReduction_zeroFullPrice_reductionNotSaved() {
        SkuReductionTo to = reductionTo(2, BigDecimal.ZERO, List.of());

        service.saveSkuReduction(to);

        verify(skuFullReductionDao, never()).insert(any(SkuFullReductionEntity.class));
    }

    @Test
    void saveSkuReduction_positiveFullPrice_reductionSaved() {
        SkuReductionTo to = reductionTo(0, new BigDecimal("500"), List.of());
        to.setReducePrice(new BigDecimal("50"));

        service.saveSkuReduction(to);

        verify(skuFullReductionDao).insert(any(SkuFullReductionEntity.class));
    }

    // ── memberPrice saved when present ────────────────────────────────────────

    @Test
    void saveSkuReduction_withMemberPrices_savesAll() {
        MemberPrice mp1 = memberPrice(1L, "金牌", "3999");
        MemberPrice mp2 = memberPrice(2L, "银牌", "4099");
        SkuReductionTo to = reductionTo(0, BigDecimal.ZERO, List.of(mp1, mp2));

        service.saveSkuReduction(to);

        verify(memberPriceService).saveBatch(argThat(list ->
                list.size() == 2
                && list.stream().findFirst().map(e -> "金牌".equals(e.getMemberLevelName())).orElse(false)));
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private SkuReductionTo reductionTo(int fullCount, BigDecimal fullPrice, List<MemberPrice> memberPrices) {
        SkuReductionTo to = new SkuReductionTo();
        to.setSkuId(100L);
        to.setFullCount(fullCount);
        to.setFullPrice(fullPrice);
        to.setDiscount(BigDecimal.ZERO);
        to.setCountStatus(0);
        to.setReducePrice(BigDecimal.ZERO);
        to.setPriceStatus(0);
        to.setMemberPrice(memberPrices);
        return to;
    }

    private MemberPrice memberPrice(Long id, String name, String price) {
        MemberPrice mp = new MemberPrice();
        mp.setId(id);
        mp.setName(name);
        mp.setPrice(new BigDecimal(price));
        return mp;
    }
}
