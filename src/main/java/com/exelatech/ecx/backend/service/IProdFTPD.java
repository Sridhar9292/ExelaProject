package com.exelatech.ecx.backend.service;

import java.util.ArrayList;

public interface IProdFTPD {
    String GetFileNameWithOutExtension();
    ArrayList<String> GetSource();
}