package com.example.yu.framework.bean;

import com.example.yu.framework.util.CastUtil;
import com.example.yu.framework.util.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@AllArgsConstructor
public class Param {
    private Map<String,Object> paramMap;

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    /**
     * Get the long parameter value based on the parameter name
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }


    /**
     * The verification parameter is emptyView
     * @return
     */
    public boolean isEmpty() {
        return CollectionUtil.isEmpty(this.paramMap);
    }
}
