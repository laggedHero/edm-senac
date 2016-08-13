package net.laggedhero.anotherapplication.realmlist.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Task extends RealmObject {
    @PrimaryKey
    public String nome;
    public String descricao;
    public long termino;
    public String local;
    public boolean iniciada;
}
