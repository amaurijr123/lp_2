import java.time.*
import java.sql.*

//val conexao : Connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\amaur\\Documents\\Codigos\\TrabalhoLP2\\lp2.sqlite")
enum class PLANO{
    AMIL, UNIMED, AMS, ASSIM, GOLDEN, OUTRO, PARTICULAR
}

 open class Contato(n: String, p: PLANO, tel: String, em: String) { //classe aberta para herança

     var nome: String = n
     var plano: PLANO = p
     var telefone: String = tel
     var email: String = em

 }

class ContatoEmpresa(n: String, p: PLANO, tel: String, em: String, var nomeEmpresa: String, var cnpj: String) : //sub-classe de Contato
    Contato(n, p, tel, em)

class Clientes{
    private var clientes: MutableMap<Contato, LocalDate> //modificado para MutableMap para ser possível a troca de elementos

    constructor(){
        this.clientes = HashMap()
    }

    constructor(clientes: MutableMap<Contato, LocalDate>){
        this.clientes = clientes
    }

    fun agendaCliente(contato: Contato, data: LocalDate){
        if(clientes.containsKey(contato)){  //procura se o Contato já existe no sistema
            if(clientes[contato]!!.isBefore(data)){ //verifica se a data no sistema é anterior a data atual
                clientes[contato] = data    //modifica a data para a data mais atual
                //dbModify(contato,data)
            }
        }else{
            clientes[contato] = data    //se não existir, insere o contato no sistema
            //dbInsert(contato,data)
        }
    }

    override fun toString(): String {
        val entries = clientes.entries //pega as entradas do Map
        var retString = ""
        entries.forEach { i ->
            retString += i.key.nome + " " + i.value.dayOfMonth.toString() + "/" + i.value.monthValue.toString() + " " //cria uma string com "Nome Dia/Mes" de todos os clientes
           println(i.key.nome + " " + i.value.dayOfMonth.toString() + "/" + i.value.monthValue.toString()) //printa a string "Nome Dia/Mes"
        }
        return retString
    }
}

/*Banco de Dados não finalizado */

//fun dbInsert(cliente: Contato, dataAtual: LocalDate){
//
//    val n = cliente.nome
//    val p = cliente.plano.name
//    val t = cliente.telefone
//    val e = cliente.email
//    val d = Date.valueOf(dataAtual)
//    val sqlT = """
//        SELECT(SELECT id FROM Clientes
//        WHERE nome = 'Fulano') AS id;
//    """.trimIndent()
//    var query: PreparedStatement = conexao.prepareStatement(sqlT)
//    query.execute()
//    val res = query.resultSet
//    if(res) {
//        val sql = """
//            INSERT INTO Clientes(
//            nome, plano, tel, email, data
//            ) VALUES ('$n', '$p', '$t', '$e', $d);
//        """.trimIndent()
//        query = conexao.prepareStatement(sql)
//        query.execute()
//    }
//}
//
//fun dbGet(){
//
//    val sql = "SELECT * FROM Clientes;"
//    val clientes = conexao.prepareStatement(sql)
//    val result = clientes.executeQuery()
//    while(result.next()){
//        var id = result.getInt(1)
//        val nome = result.getString(2)
//        val plano = result.getString(3)
//        val tel = result.getString(4)
//        val email = result.getString(5)
//        val data = result.getDate(6)
//
//        val p : PLANO = PLANO.valueOf(plano)
//        val d :LocalDate = data.toLocalDate()
//        val contato = Contato(nome,p,tel,email)
//        Clientes().agendaCliente(contato,d)
//    }
//}
//
//fun dbModify (contato : Contato, data: LocalDate){
//    val sql = """
//        UPDATE Clientes
//        SET data = $data
//        WHERE nome = ${contato.nome}
//    """.trimIndent()
//    val com = conexao.prepareStatement(sql)
//    val result = com.executeUpdate()
//}


fun main() {

    //dbGet()
    val clientes = Clientes()
    val fulano = Contato("Fulano", PLANO.PARTICULAR, "2222222", "fulano@dominio.com")
    val beltrano = ContatoEmpresa("Beltrano", PLANO.GOLDEN, "33333333", "beltrano@dominio.com", "UFF", "11111")
    clientes.agendaCliente(fulano,LocalDate.now ())
    clientes.agendaCliente(beltrano, LocalDate.now())
    //clientes.agendaCliente(fulano, LocalDate.of(2023,2,27))
    val rString = clientes.toString()

    }