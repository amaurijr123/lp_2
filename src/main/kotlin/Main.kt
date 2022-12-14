import java.awt.desktop.OpenFilesEvent
import java.beans.BeanProperty
import java.time.*
import java.io.*
import java.sql.*

enum class PLANO{
    AMIL, UNIMED, AMS, ASSIM, GOLDEN, OUTRO, PARTICULAR
}
 open class Contato(n: String, p: PLANO, tel: String, em: String) {

     var nome: String = n
     var plano: PLANO = p
     var telefone: String = tel
     var email: String = em

 }
class Clientes{
    private var clientes: MutableMap<Contato, LocalDate>

    constructor(){
        this.clientes = HashMap()
    }
    constructor(clientes: MutableMap<Contato, LocalDate>){
        this.clientes = clientes
    }
    fun agendaCliente(contato: Contato, data: LocalDate){
        if(clientes.containsKey(contato)){
            if(clientes[contato]!!.isBefore(data)){
                clientes[contato] = data
            }
        }else{
            clientes[contato] = data
        }
    }

    override fun toString(): String {
        val entries = clientes.entries
        var retString : String = ""
        entries.forEach { i ->
            retString += i.key.nome + " " + i.value.dayOfMonth.toString() + "/" + i.value.monthValue.toString()
           println(i.key.nome + " " + i.value.dayOfMonth.toString() + "/" + i.value.monthValue.toString())
        }
        return retString
    }
}

class ContatoEmpresa(n: String, p: PLANO, tel: String, em: String, var nomeEmpresa: String, var CNPJ: String) :
    Contato(n, p, tel, em) {
}

//fun dbInsert(conexao: Connection, cliente: Contato){
//
//    if (cliente)
//    val sql = """
//        INSERT INTO Clientes(
//        nome, plano, tel, email, n_emp, cnpj, data
//        ) VALUES ($cliente.nome, $cliente.plano, $cliente.telefone, $cliente.email, $cliente. );
//    """.trimIndent()
//    val query: PreparedStatement = conexao.prepareStatement(sql)
//    query.execute()
//}
//
//fun dbGet(conexao: Connection){
//
//    val sql = "SELECT * FROM Clientes;"
//    val clientes = conexao.prepareStatement(sql)
//    val result = clientes.executeQuery()
//    while(result.next()){
//        val id = result.getInt(1)
//        val nome = result.getString(2)
//        val data = result.getDate(3)
//        Clientes().agendaCliente()
//    }
//}
fun main() {

    val conexao = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\amaur\\Documents\\Codigos\\TrabalhoLP2\\lp2.sqlite")
    val clientes = Clientes()
    val fulano = Contato("Fulano", PLANO.PARTICULAR, "2222222", "fulano@dominio.com")
    val beltrano = ContatoEmpresa("Beltrano", PLANO.GOLDEN, "33333333", "beltrano@dominion.com", "UFF", "11111")
    clientes.agendaCliente(fulano, LocalDate.now())
    clientes.agendaCliente(beltrano, LocalDate.now())
    val rString = clientes.toString()

    }