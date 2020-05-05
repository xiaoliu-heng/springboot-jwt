package cn.xiaoliublog.demo.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class User {
    @NonNull private String username;
    @NonNull private String password;
}
