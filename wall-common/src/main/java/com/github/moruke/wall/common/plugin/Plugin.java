package com.github.moruke.wall.common.plugin;

public interface Plugin<IN extends Request, OUT extends Response> {

    Response execute(Request request);

    void executeNoReturn(Request request);

}
