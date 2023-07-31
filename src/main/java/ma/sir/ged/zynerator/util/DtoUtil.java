package ma.sir.ged.zynerator.util;

import ma.sir.ged.zynerator.dto.BaseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DtoUtil {


    public static List<Long> getIdsListDto(List<? extends BaseDto> list) {
        List<Long> result = new ArrayList<Long>();
        if (list != null && !list.isEmpty()) {
            for (BaseDto obj : list) {
                result.add(obj.getId());
            }
        }
        return result;
    }


    public static List<Long> getIdsList(List<? extends BaseDto> list) {
        List<Long> result = new ArrayList();
        if (list != null && !list.isEmpty()) {
            for (BaseDto obj : list) {
                if (obj.getId() == null)
                    continue;
                result.add(obj.getId());
            }
        }
        return result;
    }

    public static List<Long> getIdsListDto(Set<? extends BaseDto> list) {
        List<Long> result = new ArrayList<Long>();
        if (list != null && !list.isEmpty()) {
            for (BaseDto obj : list) {
                result.add(obj.getId());
            }
        }
        return result;
    }
}
