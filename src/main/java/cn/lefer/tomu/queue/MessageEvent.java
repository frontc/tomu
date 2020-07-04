package cn.lefer.tomu.queue;

import cn.lefer.tomu.entity.PlayHistory;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/4
 * @Description : 消息实体
 */
public class MessageEvent {
    String action;//操作类型
    String type;//值类型
    Object value;//值

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "action='" + action + '\'' +
                ", type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
