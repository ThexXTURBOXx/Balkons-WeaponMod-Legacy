package ckathode.weaponmod.core;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class WMTransformationService implements ITransformationService {

    public static final Logger LOGGER = LogManager.getLogger();

    public WMTransformationService() {
        try {
            URL loc = WMTransformationService.class.getProtectionDomain().getCodeSource().getLocation();
            ClassLoader cl = Launcher.class.getClassLoader();
            if (cl instanceof URLClassLoader) {
                Method addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addUrl.setAccessible(true);
                addUrl.invoke(cl, loc);
            } else {
                LOGGER.warn("Cannot inject BWM:L into classpath!");
            }
        } catch (Throwable t) {
            LOGGER.error("Error injecting BWM:L into classpath!", t);
        }
    }

    @NotNull
    @Override
    public String name() {
        return "weaponmod_transformer";
    }

    @Override
    public void initialize(@NotNull IEnvironment environment) {
    }

    @Override
    public void beginScanning(@NotNull IEnvironment environment) {
    }

    @Override
    public void onLoad(@NotNull IEnvironment env, @NotNull Set<String> otherServices) {
    }

    @NotNull
    @Override
    @SuppressWarnings("rawtypes")
    public List<ITransformer> transformers() {
        return Collections.singletonList(new LootTableManagerTransformer());
    }

    public static class LootTableManagerTransformer implements ITransformer<ClassNode>, Opcodes {

        private static final String TARGET_CLASS = "net.minecraft.world.storage.loot.LootTableManager";

        @NotNull
        @Override
        public ClassNode transform(ClassNode input, @NotNull ITransformerVotingContext context) {
            if (input.name.replace('/', '.').equals(TARGET_CLASS)) {
                transformLootTableManager(input);
            }
            return input;
        }

        private void transformLootTableManager(ClassNode cn) {
            for (MethodNode m : cn.methods) {
                if ((m.name.equals("onResourceManagerReload") || m.name.equals("func_195410_a"))
                    && m.desc.equals("(Lnet/minecraft/resources/IResourceManager;)V")) {

                    patchOnResourceManagerReload(m);
                    LOGGER.info("Successfully fixed LootTableManager#onResourceManagerReload logic!");
                    break;
                }
            }
        }

        private void patchOnResourceManagerReload(MethodNode method) {
            AbstractInsnNode targetInsn = null;

            // Find iresource.getPackName().equals("Default")
            for (AbstractInsnNode insn : method.instructions.toArray()) {
                if (insn.getOpcode() == INVOKEVIRTUAL) {
                    MethodInsnNode mInsn = (MethodInsnNode) insn;
                    if (mInsn.owner.equals("java/lang/String") && mInsn.name.equals("equals") &&
                        mInsn.desc.equals("(Ljava/lang/Object;)Z")) {

                        AbstractInsnNode prevLdc = mInsn.getPrevious();
                        if (prevLdc != null && prevLdc.getOpcode() == LDC && "Default".equals(((LdcInsnNode) prevLdc).cst)) {
                            targetInsn = mInsn;
                            break;
                        }
                    }
                }
            }

            if (targetInsn == null) {
                LOGGER.error("Could not locate target instructions in LootTableManager!");
                return;
            }

            // iresource.getPackName().equals("Default")
            // ->
            // iresource == null || !iresource.getPackName().equals("Default")

            // IXOR 1 is basically the same as NOT
            InsnList invertList = new InsnList();
            invertList.add(new InsnNode(ICONST_1));
            invertList.add(new InsnNode(IXOR));
            method.instructions.insert(targetInsn, invertList);

            AbstractInsnNode ldcNode = targetInsn.getPrevious();
            AbstractInsnNode getPackNameNode = ldcNode.getPrevious();
            AbstractInsnNode aloadIResourceNode = getPackNameNode.getPrevious();

            LabelNode labelTrue = new LabelNode();
            LabelNode labelEnd = new LabelNode();

            InsnList nullCheckHead = new InsnList();
            nullCheckHead.add(new InsnNode(DUP));
            nullCheckHead.add(new JumpInsnNode(IFNULL, labelTrue));
            method.instructions.insertBefore(aloadIResourceNode, nullCheckHead);

            InsnList nullCheckTail = new InsnList();
            nullCheckTail.add(new JumpInsnNode(GOTO, labelEnd));
            nullCheckTail.add(labelTrue);
            nullCheckTail.add(new InsnNode(ICONST_0));
            nullCheckTail.add(labelEnd);

            AbstractInsnNode ixorNode = targetInsn.getNext().getNext();
            method.instructions.insert(ixorNode, nullCheckTail);
        }

        @NotNull
        @Override
        public Set<Target> targets() {
            return Collections.singleton(Target.targetClass(TARGET_CLASS));
        }

        @NotNull
        @Override
        public TransformerVoteResult castVote(@NotNull ITransformerVotingContext context) {
            return TransformerVoteResult.YES;
        }

    }

}
