package us.lsi.dp1.newcorporder.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class OffsetPageable extends PageRequest {

    public static OffsetPageable of(long offset, int size) {
        return new OffsetPageable(offset, size);
    }

    private final long offset;

    private OffsetPageable(long offset, int size) {
        super((int) offset, size, Sort.unsorted());
        this.offset = offset;
    }

    @Override
    public long getOffset() {
        return this.offset;
    }
}
